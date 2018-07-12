package lakers.ingram.Action;

import lakers.ingram.ModelEntity.CommentEntity;
import lakers.ingram.ModelEntity.FoodEntity;
import lakers.ingram.ModelEntity.WindowEntity;
import lakers.ingram.service.AppService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping(value = "/Comment")
public class CommentAction extends HttpServlet {
    @Autowired
    private AppService appService;

    @RequestMapping(value = "/SaveComment")//发表评论
    private void processSaveComment(
            @RequestParam("userId") int userId,
            @RequestParam("windowId") int windowId,
            @RequestParam("commentContent") String commentContent,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        CommentEntity comment = new CommentEntity();
        comment.setUserId(userId);
        comment.setWindowId(windowId);
        comment.setCommentContent(commentContent);
        comment.setValid((byte)1);
        appService.CommentSave(comment);

        List<CommentEntity> commentList = appService.CommentListGetByWindowId(windowId,(byte)1);
        PrintWriter out = response.getWriter();
        JSONArray arr = JSONArray.fromObject(commentList);
        out.println(CommentEntityAddUserName(arr).toString());
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/DeleteComment")//删除评论
    private void processDeleteComment(   @RequestParam("commentId") int commentId,
                                       HttpServletRequest request,
                                       HttpServletResponse response)  throws Exception {

        PrintWriter out = response.getWriter();
        appService.CommentDelete(commentId);
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/ManageComment")//封禁或解封评论
    private void processManageComment(@RequestParam("commentId") int commentId,
                                       @RequestParam("valid") int valid,
                                       HttpServletRequest request,
                                       HttpServletResponse response)  throws Exception {

       appService.CommentUpdate(commentId,valid);

        PrintWriter out = response.getWriter();

        out.flush();
        out.close();
    }

    @RequestMapping(value = "/AllCommentByWindowId")//拿到所有的评论列表
    private void processAllComment( @RequestParam("windowId") int windowId,
                                      HttpServletRequest request,
                                      HttpServletResponse response)  throws Exception {
        byte x = 1;
        List<CommentEntity>commentList = appService.CommentListGetByWindowId(windowId,x);
        PrintWriter out = response.getWriter();
        JSONArray arr = JSONArray.fromObject(commentList);
        out.println(CommentEntityAddUserName(arr).toString());
        System.out.println(CommentEntityAddUserName(arr).toString());
        out.flush();
        out.close();
    }

    public JSONArray CommentEntityAddUserName(List<CommentEntity>commentList){
        WindowEntity win = new WindowEntity();
        JSONArray arr = JSONArray.fromObject(commentList);
        JSONArray arr2 = new JSONArray();
        for(int i= 0;i<arr.size();i++){
            JSONObject comment = arr.getJSONObject(i);
            String userName= appService.getUserById((int)comment.get("userId")).getUsername();
            comment.put("userName",userName);
            arr2.add(comment);
        }
        return arr2;
    }


}
