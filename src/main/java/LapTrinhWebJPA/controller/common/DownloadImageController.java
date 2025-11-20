package LapTrinhWebJPA.controller.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/image") // Ví dụ gọi: /image?fname=uploads/product/123.jpg
public class DownloadImageController extends HttpServlet {

	private static final int BUFFER_SIZE = 4096;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Tham số fname sẽ chứa đường dẫn tương đối (VD: uploads/category/tenfile.jpg)
		String fileName = req.getParameter("fname");

		if (fileName == null || fileName.isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "File name parameter is missing.");
			return;
		}

		// Lấy đường dẫn vật lý tuyệt đối của file trên server
		// getRealPath("/") trả về thư mục root của webapp
		String absolutePath = getServletContext().getRealPath("/") + fileName;
		File file = new File(absolutePath);

		// Kiểm tra file có tồn tại không
		if (!file.exists() || file.isDirectory()) {
			// Nếu không tìm thấy, có thể trả về một ảnh mặc định (placeholder)
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found: " + fileName);
			return;
		}

		// Thiết lập Content Type
		String contentType = getServletContext().getMimeType(file.getAbsolutePath());
		if (contentType == null) {
			contentType = "image/jpeg"; // Mặc định
		}

		resp.setContentType(contentType);
		resp.setContentLength((int) file.length());

		// Đọc file và ghi ra response
		try (FileInputStream fileInputStream = new FileInputStream(file);
				OutputStream outputStream = resp.getOutputStream()) {

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead;

			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			System.err.println("Error sending file: " + fileName);
			// e.printStackTrace(); // Không nên in stack trace ra console production quá
			// nhiều
		}
	}
}