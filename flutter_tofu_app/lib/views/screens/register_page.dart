import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_tofu_app/bloc/register/register_cubit.dart';
import 'package:flutter_tofu_app/views/views.dart';
import 'package:flutter_tofu_app/views/widgets/register_form_widget.dart';

class RegisterPage extends StatelessWidget {
  const RegisterPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color.fromARGB(255, 230, 230, 230),
      appBar: AppBar(
        backgroundColor: Color.fromARGB(255, 155, 214, 100),
        title: Row(children: [
          Icon(size: 35, Icons.person),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 20.0),
            child: Text("Register"),
          )
        ]),
      ),
      body: SafeArea(
          minimum: EdgeInsets.all(3),
          child: BlocProvider<RegisterCubit>(
              create: (context) => RegisterCubit(),
              child: RegisterForm(),
              )),
    );
  }
}
