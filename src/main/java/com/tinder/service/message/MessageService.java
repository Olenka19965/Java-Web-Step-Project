package com.tinder.service.message;

import com.tinder.dao.message.MessageDao;
import com.tinder.model.Message;
import com.tinder.exception.DaoException;
import com.tinder.exception.ServiceException;

import java.util.List;

public class MessageService {
    private final MessageDao dao;

    public MessageService(MessageDao dao) {
        this.dao = dao;
    }

    public void create(Message m) throws ServiceException {
        try {
            dao.create(m);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void update(Message m) throws ServiceException {
        try {
            dao.update(m);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void delete(int id) throws ServiceException {
        try {
            dao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Message> readSome(int senderId, int receiverId, int offset, int limit) throws ServiceException {
        try {
            return dao.readSome(senderId, receiverId, offset, limit);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
