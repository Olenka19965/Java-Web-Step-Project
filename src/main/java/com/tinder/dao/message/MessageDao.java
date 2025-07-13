package com.tinder.dao.message;


import com.tinder.dao.ListDao;
import com.tinder.model.Message;
import com.tinder.exception.DaoException;

import java.util.List;

public interface MessageDao extends ListDao<Message> {
    List<Message> readSome(int senderId, int receiverId, int offset, int limit) throws DaoException;
}
