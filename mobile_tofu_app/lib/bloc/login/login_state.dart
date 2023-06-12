import 'package:equatable/equatable.dart';


abstract class LoginState extends Equatable{
  @override
  List<Object> get props => [];
}

class LoginInitialState extends LoginState {}

class LoginLoadingState extends LoginState {}

class LoginSuccessState extends LoginState {}

class LoginFailureState extends LoginState {
  final String error;

  LoginFailureState({required this.error});

  @override
  List<Object> get props => [error];
}