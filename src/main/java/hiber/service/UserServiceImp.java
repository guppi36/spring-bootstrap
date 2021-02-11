package hiber.service;

import hiber.model.User;
import hiber.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   private final UserRepository userRepository;

   @Autowired
   public UserServiceImp(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   public void add(User user) {
      userRepository.save(user);
   }

   @Override
   public List<User> listUsers() {
      return userRepository.findAll();
   }

   @Override
   public void delete(User user) { userRepository.delete(user); }

   @Override
   public void update(User user) {
      userRepository.deleteById(user.getId());
      userRepository.save(user);
   }

   @Override
   public User getUserByName(String username) {
      return userRepository.findByUsername(username);
   }
}
