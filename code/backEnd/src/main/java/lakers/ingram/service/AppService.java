package lakers.ingram.service;

import lakers.ingram.ModelEntity.UserEntity;
import lakers.ingram.ModelEntity.WindowEntity;

import java.util.List;

public interface AppService {
    //User
    public Integer addUser(UserEntity user);

    public void deleteUser(UserEntity user);

    public void updateUser(UserEntity user);

    public UserEntity getUserById(int id);

    public UserEntity getUserByName(String name);

    public UserEntity getUserByPhone(String phone);

    public List<UserEntity> getAllUsers();

    //Floor
    public List<Integer> getFloorListByRestaurant(String restaurant);

    //Window
    public List<WindowEntity> getAllWindowsByRestaurant(String restaurant);

    public List<WindowEntity> getAllWindowsByRestaurantAndFloor(String restaurant, int floor);

    public WindowEntity getWindowByRestaurantAndFloorAndName(String restaurant, int floor, String windowName);

    public List<WindowEntity> getAllWindows();



}
