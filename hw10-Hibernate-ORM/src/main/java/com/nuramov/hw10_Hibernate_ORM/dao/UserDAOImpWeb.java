package com.nuramov.hw10_Hibernate_ORM.dao;

import com.nuramov.hw10_Hibernate_ORM.HibernateUtil;
import com.nuramov.hw10_Hibernate_ORM.model.User;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

/**
 * class UserDAOImp реализует интерфейс UserDAO и позволяет работать с БД H2 через Hibernate в web приложении
 * Дополнительно реализован метод getAllUser для вывода списка всех User'ов
 * Работа класса UserDAOImp в web приложении реализована без слоя данных UserService,
 * отвечающего за выполнение бизнес-логики, а напрямую.
 */
public class UserDAOImpWeb implements UserDAO{

    @Override
    public Optional<User> findById(long id) {
        Optional<User> optionalUser;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Для исключения возможности получения значения null и NullPointerException в будущем,
            // воспользовались Optional - обертку вокруг User
            optionalUser = Optional.ofNullable(session.get(User.class, id));
        }
        return optionalUser;
    }

    @Override
    public long save(User user) {
        // id User'a в БД
        long id;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                // Сохраняем пользователя (User) в БД и возвращаем его id
                id = (long) session.save(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
        return id;
    }

    @Override
    public void update(User user) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                // Обновляем данные пользователя (User) в БД
                session.update(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void delete(User user) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                // Сначала присваиваем полю phone значение null, чтобы в случае удаления пользователя (User) из БД
                // не был удален Phone, на которого ссылаются другие пользователи
                user.setPhone(null);
                session.update(user);
                // Удаляем пользователя (User) из БД
                session.delete(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Метод getAllUser позволяет получить список всех User'ов
     * @return - список User'ов
     */
    public List<User> getAll() {
        List<User> listOfUser;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();

                // Получаем список всех User'ов через запрос
                listOfUser = session.createQuery("from User").getResultList();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
        return listOfUser;
    }
}
