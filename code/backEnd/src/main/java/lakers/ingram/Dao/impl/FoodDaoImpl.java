package lakers.ingram.Dao.impl;

import lakers.ingram.Dao.FoodDao;
import lakers.ingram.HibernateUtil.HibernateUtil;
import lakers.ingram.ModelEntity.FoodEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository("FoodDao")
@Transactional
class FoodDaoImpl implements FoodDao {

    @Override
    public List<FoodEntity> getAllFood() {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query =session.createQuery("select food " +
                "from FoodEntity food " +
                "where foodId in (select foodId from TodayfoodEntity todayfood) " +
                "order by food.likes desc, windowId");
        @SuppressWarnings("unchecked")
        List<FoodEntity> foods = query.list();
        session.getTransaction().commit();
        return foods;
    }

    @Override
    public List<FoodEntity> getAllFoodByRestaurant(String restaurant) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query =session.createQuery("select food " +
                "from FoodEntity food " +
                "where foodId in (select foodId from TodayfoodEntity todayfood " +
                "where food.windowId in " +
                "(select distinct window.windowId from WindowEntity window where " +
                "window.restaurant = :restaurant)) " +
                "order by food.likes desc,food.windowId ");
        query.setParameter("restaurant", restaurant);
        @SuppressWarnings("unchecked")
        List<FoodEntity> foods = query.list();
        session.getTransaction().commit();

        return foods;
    }

    @Override
    public List<FoodEntity> getAllFoodByRestaurantAndFloor(String restaurant, int floor) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query =session.createQuery("select food " +
                "from FoodEntity food " +
                "where foodId in (select foodId from TodayfoodEntity todayfood " +
                "where windowId in " +
                "(select distinct windowId from WindowEntity window where " +
                "window.restaurant = :restaurant and window.floor = :floor ))" +
                "order by likes desc,windowId ");
        query.setParameter("restaurant", restaurant);
        query.setParameter("floor", floor);
        @SuppressWarnings("unchecked")
        List<FoodEntity> foods = query.list();
        session.getTransaction().commit();
        return foods;
    }


    @Override
    public List<FoodEntity> getAllFoodByWindowid(int window_id) {
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query =session.createQuery("select food " +
                "from FoodEntity food " +
                "where foodId in (select foodId from TodayfoodEntity todayfood " +
                "where windowId = :windowId) " +
                "order by likes desc ");
        query.setParameter("windowId", window_id);
        @SuppressWarnings("unchecked")
        List<FoodEntity> foods = query.list();
        session.getTransaction().commit();
        return foods;
    }

    @Override
    public String getWindowNameByFoodId(int foodId){
        Session session=HibernateUtil.getSession();
        session.beginTransaction();

        Query query =session.createQuery("select windowName " +
                "from WindowEntity window " +
                "where windowId in (select windowId from FoodEntity food  " +
                "where foodId = :foodId) ");
        query.setParameter("foodId", foodId);
        @SuppressWarnings("unchecked")
        List<String> windowNames = query.list();
        session.getTransaction().commit();
        String windowName = windowNames.size() > 0 ? windowNames.get(0) : null;
        return windowName;
    }

    public List<FoodEntity> getAllFoodByLikeStr(String str){
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query= session.createQuery("select food f" +
                "rom FoodEntity food where food.foodName like :name or food.tips like :tip")
                .setParameter("name","%"+str+"%").setParameter("tip","%"+str+"%");
        @SuppressWarnings("unchecked")
        List<FoodEntity> lst=query.list();
        session.getTransaction().commit();
        return lst;
    }

    public Integer getWindowIdByFoodIdAndTime(int foodId, int time){
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query= session.createQuery("select food.windowId " +
                "from TodayfoodEntity food where food.foodId= :id and food.time= :time")
                .setParameter("id",foodId).setParameter("time",time);
        @SuppressWarnings("unchecked")
        List<Integer> lst=query.list();
        Integer id=lst.size()>0 ? lst.get(0):null;
        session.getTransaction().commit();
        return id;
    }

    public FoodEntity getFoodById(int foodId){
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
        Query query= session.createQuery("select food from FoodEntity food where food.foodId= :id")
                .setParameter("id",foodId);
        @SuppressWarnings("unchecked")
        List<FoodEntity> foods=query.list();
        session.getTransaction().commit();
        return foods.size()>0?foods.get(0):null;
    }

    @Override
    public Map<String, String> getFoodId_NameByWindowID(int windowID)
    {
        System.out.println(windowID);
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("select everyfood " +
                "from FoodEntity everyfood " +
                "where everyfood.windowId= :window_ID ")
                .setParameter("window_ID", windowID);

        @SuppressWarnings("unchecked")
        List<FoodEntity> foodList = query.list();
        System.out.println(foodList);

        Map<String, String> resultList = new LinkedHashMap<>();
        for(FoodEntity singleFood : foodList)
        {
            Integer foodID = singleFood.getFoodId();
            String foodName = singleFood.getFoodName();
            String strID = String.valueOf(foodID);
            resultList.put(strID, foodName);
        }

        transaction.commit();
        session.close();
        return resultList;
    }
}
