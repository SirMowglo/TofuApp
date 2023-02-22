import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:rxdart/rxdart.dart';

import '../../data/repositories/authentication_repository.dart';

part 'register_state.dart';

class RegisterCubit extends Cubit<RegisterState> {
  RegisterCubit() : super(RegisterInitial());
  final authRepository = AuthenticationRepository();

  final _fullnameController = BehaviorSubject<String>();
  final _usernameController = BehaviorSubject<String>();
  final _passwordController = BehaviorSubject<String>();
  final _verifiedPasswordController = BehaviorSubject<String>();
  final _emailController = BehaviorSubject<String>();
  final _verifiedEmailController = BehaviorSubject<String>();

  Stream<String> get usernameStream => _usernameController.stream;
  Stream<String> get fullnameStream => _fullnameController.stream;
  Stream<String> get passwordStream => _passwordController.stream;
  Stream<String> get verifiedPasswordStream => _verifiedPasswordController.stream;
  Stream<String> get emailStream => _emailController.stream;
  Stream<String> get verifiedEmailStream => _verifiedEmailController.stream;

  void clean() {
    updateUserName('');
    updatePassword('');
    updateEmail('');
    updateFullName('');
    updateVerifiedEmail('');
    updateVerifiedPassword('');
  }

  //! Validation of UserName
  void updateUserName(String userName) {
    if (userName.length < 0) {
      _usernameController.sink.addError("The username field can't be empty");
    } else if (userName.containsSpecialChar) {
      _usernameController.sink.addError("The username can't contain spaces or special characters");
    } else {
      _usernameController.sink.add(userName);
    }
  }

  //! Validation of FullName
  void updateFullName(String fullName) {
    if (fullName.length < 0) {
      _fullnameController.sink.addError("The full name field can't be empty");
    } else {
      _fullnameController.sink.add(fullName);
    }
  }

  //! Validation of Password
  void updatePassword(String password) {
    if (password.length < 8) {
      _passwordController.sink.addError("The password need to be longer than 8 characters");
    } else if (!password.containsLowercase && !password.containsUppercase) {
      _passwordController.sink
          .addError("The password needs to have both lowercase and uppercase characters");
    } else if (!password.containsNumber) {
      _passwordController.sink.addError("The password needs to have at least one number");
    } else {
      _passwordController.sink.add(password);
    }
  }

  //! Validation of Verified Password
  void updateVerifiedPassword(String verifiedPassword) {
    if (_passwordController.hasValue && verifiedPassword != _passwordController.value) {
      _verifiedPasswordController.sink
          .addError("The verified password doesn't match the current password");
    } else {
      _verifiedPasswordController.sink.add(verifiedPassword);
    }
  }

  //! Validation of Email
  void updateEmail(String email) {
    if (!email.isEmail) {
      _emailController.sink.addError('The current value is not an email');
    } else {
      _emailController.sink.add(email);
    }
  }

  //! Validation of Verified Email
  void updateVerifiedEmail(String verifiedEmail) {
    if (_emailController.hasValue && verifiedEmail != _emailController.value) {
      _verifiedEmailController.sink.addError("The verified email doesn't match the current email");
    } else {
      _verifiedEmailController.sink.add(verifiedEmail);
    }
  }

  Stream<bool> get validateForm => Rx.combineLatest6(
        usernameStream,
        passwordStream,
        verifiedEmailStream,
        verifiedPasswordStream,
        verifiedEmailStream,
        fullnameStream,
        (a, b, c, d, e, f) => true,
      );

  void register() {
    authRepository.register(
        _usernameController.value,
        _emailController.value,
        _verifiedEmailController.value,
        _passwordController.value,
        _verifiedPasswordController.value,
        _fullnameController.value);
  }
}

extension StringValidators on String {
  bool get containsUppercase => contains(RegExp(r'[A-Z]'));
  bool get containsLowercase => contains(RegExp(r'[a-z]'));
  bool get containsNumber => contains(RegExp(r'[0-9]'));
  bool get isEmail => contains(RegExp(r'^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$'));
  bool get containsSpecialChar => contains(RegExp(r'(?:[A-z]|[0-9]|_|-)'));
}
