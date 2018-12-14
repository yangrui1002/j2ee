package controller;

import DAO.TopicDao;
import pojo.Topic;

import java.util.List;

/**
 * Created by YR on 2018/12/14.
 */
public class TopicService {
    TopicDao topicDao = new TopicDao ();

    public boolean senttopic(String title, String message, String uid){
        return topicDao.senttopic (title, message, uid);
    }

    public boolean sentmessage(String tid, String message, String uid){
        return topicDao.sentmessage (tid, message, uid);
    }

    public List<Topic> tlist(){return topicDao.tlist ();}

    public List<Topic> mlist(String tid){return topicDao.mlist (tid);}
}
