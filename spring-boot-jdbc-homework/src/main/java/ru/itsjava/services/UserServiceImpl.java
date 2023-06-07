package ru.itsjava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.itsjava.dao.UserDao;
import ru.itsjava.domain.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    // Вешаем @Autowired над конструктором и указываем в конструкторе @Qualifier явно, потому что UserDao имеет две реализации:
    @Autowired
    public UserServiceImpl(@Qualifier("userDaoImpl") UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int count() {
        return userDao.count();
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User insert(User user) {
        return userDao.insert(user);
    }

    @Override
    public void updateById(User user, long id) {
        userDao.updateById(user, id);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public User findById(long id) {
        return userDao.findById(id);
    }
}
