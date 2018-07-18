package br.com.concrete.codechallenge.core.user;

import org.springframework.stereotype.Service;

import br.com.concrete.codechallenge.libs.cipher.CipherUtils;
import br.com.concrete.codechallenge.application.security.JwtUtils;

import static br.com.concrete.codechallenge.libs.validators.Preconditions.checkNotNull;

@Service
public class RegisterService {
  private final UserRepository userRepository;
  private final JwtUtils jwtUtils;

  RegisterService(UserRepository userRepository, JwtUtils jwtUtils) {
    this.userRepository = userRepository;
    this.jwtUtils = jwtUtils;
  }

  public User register(RegisterArgs args) {
    checkNotNull(args);

    if (userRepository.findByEmail(args.getEmail()).isPresent()) {
      throw new UserNotFoundException();
    }

    String salt = CipherUtils.createSalt();
    String hashedPassword = CipherUtils.createPasswordHash(args.getPassword(), salt);
    String token = jwtUtils.createJWT(args.getName());
    User user = new User(args.getName(), args.getEmail(), hashedPassword, salt, token, args.getPhones());

    userRepository.save(user);

    return user;
  }
}
