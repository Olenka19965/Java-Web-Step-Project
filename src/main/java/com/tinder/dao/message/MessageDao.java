package com.tinder.dao.message;


import com.tinder.dao.ListDao;
import com.tinder.domain.Message;
import com.tinder.exception.DaoException;

import java.util.List;

public interface MessageDao extends ListDao<Message> {
    List<Message> readSome(String senderId, String receiverId, int offset, int limit) throws DaoException;
}
