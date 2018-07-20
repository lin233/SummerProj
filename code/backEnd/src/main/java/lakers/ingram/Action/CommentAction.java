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

    @RequestMapping(value = "/Save")//发表评论
    private void processSaveComment(
            @RequestParam("windowId") int windowId,
            @RequestParam("commentContent") String commentContent,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        Integer userId=Integer.valueOf(request.getSession().getAttribute("userid").toString());
        CommentEntity comment = new CommentEntity();
        comment.setUserId(userId);
        comment.setWindowId(windowId);
        comment.setCommentContent(commentContent);
        comment.setValid((byte)1);
        appService.CommentSave(comment);
        PrintWriter out = response.getWriter();
        List<CommentEntity>commentList = appService.CommentListGetByWindowId(windowId,(byte)1);
        JSONArray arr = JSONArray.fromObject(commentList);
        JSONArray arr2 = CommentEntityAddHeadPic(arr);
        JSONArray arr3 = CommentEntityAddUserName(arr2);
        out.println(arr3.toString());
        System.out.println(arr3.toString());
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/DeleteComment")//删除评论
    private void processDeleteComment(   @RequestParam("commentId") int commentId,
                                         @RequestParam("windowId") int windowId,
                                       HttpServletRequest request,
                                       HttpServletResponse response)  throws Exception {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        appService.CommentDelete(commentId);
        List<CommentEntity>commentList = appService.CommentListGetByWindowId(windowId,(byte)1);
        JSONArray arr = JSONArray.fromObject(commentList);
        JSONArray arr2 = CommentEntityAddHeadPic(arr);
        JSONArray arr3 = CommentEntityAddUserName(arr2);
        out.println(arr3.toString());
        System.out.println(arr3.toString());
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
        response.setContentType("application/json;charset=utf-8");
        List<CommentEntity>commentList = appService.CommentListGetByWindowId(windowId,(byte)1);
        PrintWriter out = response.getWriter();
        JSONArray arr = JSONArray.fromObject(commentList);
        JSONArray arr2 = CommentEntityAddHeadPic(arr);
        JSONArray arr3 = CommentEntityAddUserName(arr2);
        out.println(arr3.toString());
        System.out.println(arr3.toString());
        out.flush();
        out.close();
    }


    private JSONArray CommentEntityAddHeadPic(JSONArray arr){
        JSONArray arr2 = new JSONArray();
        for(int i= 0;i<arr.size();i++){
            JSONObject comment = arr.getJSONObject(i);
            //   String userName= appService.getUserById((int)comment.get("userId")).getUsername();
            comment.put("HeadPic","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4099675241,2760395260&fm=15&gp=0.jpg");
            arr2.add(comment);
        }
        return arr2;
    }

    private JSONArray CommentEntityAddUserName(JSONArray arr){
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
