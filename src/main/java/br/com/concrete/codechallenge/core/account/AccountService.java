package br.com.concrete.codechallenge.core.account;

import org.springframework.stereotype.Service;

import br.com.concrete.codechallenge.application.security.JwtUtils;
import br.com.concrete.codechallenge.core.user.User;
import br.com.concrete.codechallenge.core.user.UserRepository;
import br.com.concrete.codechallenge.libs.cipher.CipherUtils;

@Service
public class AccountService {
  private final UserRepository userRepository;
  private final JwtUtils jwtUtils;

  public AccountService(final UserRepository userRepository, final JwtUtils jwtUtils) {
    this.userRepository = userRepository;
    this.jwtUtils = jwtUtils;
  }

  User login(final LoginRequest args) {
    final User user = userRepository
      .findByEmail(args.getEmail())
      .orElseThrow(UnauthorizedException::new);

    final String hashed = CipherUtils.createPasswordHash(args.getPassword(), user.getSalt());

    if (!hashed.equals(user.getPassword())) {
      throw new UnauthorizedException();
    }

    user.newLogin(jwtUtils.createJWT(user.getName()));

    userRepository.save(user);

    return user;
  }
}
