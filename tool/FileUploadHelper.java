package tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.Part;

public class FileUploadHelper {

    /**
     * ファイルを指定のファイル名でアップロードする
     *
     * @param filePart アップロードされたファイル (Part)
     * @param fileName 保存するファイル名 (String)
     * @param uploadPath アップロード先ディレクトリのパス (String)
     * @return アップロード成功なら true、失敗なら false
     */
    public static boolean uploadFile(Part filePart, String fileName, String uploadPath) {
        if (filePart == null || fileName == null || fileName.isEmpty()) {
            return false; // 無効な入力
        }

        try {
            // アップロードディレクトリの作成（存在しない場合）
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // フォルダを作成
            }

            // 保存先のファイルパスを作成
            Path filePath = Paths.get(uploadPath, fileName);

            // ファイルを保存
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, filePath);
            }

            return true; // 成功
        } catch (IOException e) {
            e.printStackTrace();
            return false; // エラーが発生した場合は失敗
        }
    }
}
