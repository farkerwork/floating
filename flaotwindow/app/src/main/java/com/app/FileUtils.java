package com.app;
import com.app.*;
import android.widget.Toast;
import android.content.Context;
import java.io.File;

public class FileUtils {
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
	
		
    

        if (file.delete()) {
            System.out.println("文件删除成功: " + filePath);
			//Toast.makeText(Content, "登录按钮被点击", Toast.LENGTH_SHORT).show();
			getpermission.ts("文件删除成功: " + filePath);
        } else {
            System.out.println("删除失败，文件可能不存在或没有权限");
			getpermission.ts("删除失败，文件可能不存在或没有权限");
        }
    }

   
}
