package lakers.ingram.service.impl;

import lakers.ingram.Dao.*;
import lakers.ingram.ModelEntity.AdminEntity;
import lakers.ingram.ModelEntity.UserEntity;
import lakers.ingram.ModelEntity.WindowEntity;
import lakers.ingram.ModelEntity.WorkerEntity;
import lakers.ingram.service.AppService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.File;
import java.util.List;

@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private WorkerDao workerDao;

    @Autowired
    private WindowDao windowDao;

    @Autowired
    private UserTagDao userTagDao;



    @Autowired
    private TagDao tagDao;


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }
    public void setWorkerDao(WorkerDao workerDao) {
        this.workerDao = workerDao;
    }
    public void setWindowDao(WindowDao windowDao){ this.windowDao = windowDao; }

    //user
    public Integer addUser(UserEntity user){ return userDao.save(user); }

    public void deleteUser(UserEntity user){
        userDao.delete(user);
    }

    public void updateUser(UserEntity user){
        userDao.update(user);
    }

    public UserEntity getUserById(int id){
        return userDao.getUserById(id);
    }

    public UserEntity getUserByName(String name){ return userDao.getUserByName(name); }

    public UserEntity getUserByPhone(String phone){ return userDao.getUserByPhone(phone); }

    public List<UserEntity> getAllUsers(){ return userDao.getAllUsers(); }

    //admin
    public AdminEntity getAdminById(int id){ return adminDao.getAdminById(id); }

    //worker
    public WorkerEntity getWorkerById(int id){ return workerDao.getWorkerById(id); }

    //Floor
    public List<Integer> getFloorListByRestaurant(String restaurant){
        return windowDao.getFloorListByRestaurant(restaurant);
    };

    //Window
    public List<WindowEntity> getAllWindowsByRestaurant(String restaurant){
        return windowDao.getAllWindowsByRestaurant(restaurant);
    };

    public List<WindowEntity> getAllWindowsByRestaurantAndFloor(String restaurant, int floor){
        return windowDao.getAllWindowsByRestaurantAndFloor(restaurant,floor);
    };

    public WindowEntity getWindowByRestaurantAndFloorAndName(String restaurant, int floor, String windowName){
        return windowDao.getWindowByRestaurantAndFloorAndName(restaurant,floor,windowName);
    };

    public List<WindowEntity> getAllWindows(){
        return windowDao.getAllWindows();
    };
    public JSONArray listUserTag(Integer name){return userTagDao.listUserTag(name);}
    public String chooseUserTag(Integer userid,String[] tagArray) {
        return userTagDao.chooseUserTag(userid,tagArray);
    }

    public JSONObject showUserInfo(Integer userID){
        return userDao.showUserInfo( userID);
    }
    public String handleUserInfo(UserEntity user)throws Exception{
        return userDao.handleUserInfo(user);
    }

    public JSONArray showTags(){
        return tagDao.showTags();
    }

    public String sendTags(Integer userid,JSONArray tagArray){
        return userTagDao.sendTags(userid,tagArray);
    }

    public JSONObject showTags(String tagName){
        return tagDao.showTags(tagName);
    }

    public String updatePic(File imgFile, Integer userid){
        return userDao.updatePic(imgFile, userid);
    }

    public String newFoodPic(File imageFile,String windowid){
        return workerDao.newFoodPic(imageFile,windowid);
    }

}
