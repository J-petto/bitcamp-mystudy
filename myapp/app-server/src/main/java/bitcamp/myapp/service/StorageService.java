package bitcamp.myapp.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public interface StorageService {
    String CONTENT_TYPE = "ContentType";

    void upload(String filePath, InputStream in, Map<String, Object> options) throws Exception;
    void delete(String filePath) throws Exception;
    void download(String filePath, OutputStream out) throws Exception;
}
