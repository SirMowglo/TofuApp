import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_tofu_app/bloc/register/register_cubit.dart';

import 'custom_button_widget.dart';
import 'custom_text_form_field_widget.dart';

class RegisterForm extends StatefulWidget {
  const RegisterForm({super.key});

  @override
  State<RegisterForm> createState() => _RegisterFormState();
}

class _RegisterFormState extends State<RegisterForm> {
  RegisterCubit? _registerCubit;

  @override
  Widget build(BuildContext context) {
    _registerCubit = BlocProvider.of<RegisterCubit>(context, listen: false);

    return Form(
      child: Column(
        children: [
          //! Username field
          SizedBox(
            height: 20,
            width: 20,
          ),
          StreamBuilder(
              stream: _registerCubit?.usernameStream,
              builder: (context, snapshot) {
                return CustomTextFormField(
                  hint: "Username",
                  obscureText: false,
                  onChanged: (text) {
                    _registerCubit?.updateUserName(text!);
                  },
                  errorText:
                      snapshot.hasError ? snapshot.error.toString() : null,
                );
              }),
          //! Fullname field
          SizedBox(
            height: 20,
            width: 20,
          ),
          StreamBuilder(
              stream: _registerCubit?.fullnameStream,
              builder: (context, snapshot) {
                return CustomTextFormField(
                  hint: "Full Name",
                  obscureText: false,
                  onChanged: (text) {
                    _registerCubit?.updateFullName(text!);
                  },
                  errorText:
                      snapshot.hasError ? snapshot.error.toString() : null,
                );
              }),
          //! Password field
          SizedBox(
            height: 20,
            width: 20,
          ),
          StreamBuilder(
              stream: _registerCubit?.passwordStream,
              builder: (context, snapshot) {
                return CustomTextFormField(
                  hint: "Password",
                  obscureText: true,
                  onChanged: (text) {
                    _registerCubit?.updatePassword(text!);
                  },
                  errorText:
                      snapshot.hasError ? snapshot.error.toString() : null,
                );
              }),
          //! Verified password field
          SizedBox(
            height: 20,
            width: 20,
          ),
          StreamBuilder(
              stream: _registerCubit?.verifiedPasswordStream,
              builder: (context, snapshot) {
                return CustomTextFormField(
                  hint: "Verified password",
                  obscureText: true,
                  onChanged: (text) {
                    _registerCubit?.updateVerifiedPassword(text!);
                  },
                  errorText:
                      snapshot.hasError ? snapshot.error.toString() : null,
                );
              }),
          //! Email field
          SizedBox(
            height: 20,
            width: 20,
          ),
          StreamBuilder(
              stream: _registerCubit?.emailStream,
              builder: (context, snapshot) {
                return CustomTextFormField(
                  hint: "Email",
                  obscureText: false,
                  onChanged: (text) {
                    _registerCubit?.updateEmail(text!);
                  },
                  errorText:
                      snapshot.hasError ? snapshot.error.toString() : null,
                );
              }),
          //! Verified email| field
          SizedBox(
            height: 20,
            width: 20,
          ),
          StreamBuilder(
              stream: _registerCubit?.verifiedEmailStream,
              builder: (context, snapshot) {
                return CustomTextFormField(
                  hint: "Verified Email",
                  obscureText: false,
                  onChanged: (text) {
                    _registerCubit?.updateVerifiedEmail(text!);
                  },
                  errorText:
                      snapshot.hasError ? snapshot.error.toString() : null,
                );
              }),
          SizedBox(
            height: 20,
            width: 20,
          ),
          StreamBuilder(
              stream: _registerCubit?.validateForm,
              builder: (context, snapshot) {
                return CustomButton(
                  text: 'Register',
                  color: snapshot.hasData
                      ? Color.fromARGB(255, 155, 214, 100)
                      : Color.fromARGB(255, 122, 122, 122),
                  padding: 35,
                  textColor: Colors.white,
                  onTap: snapshot.hasData
                      ? () {
                          {
                            //! IMPORTANTE 
                            //TODO Manejo de errores del server
                            _registerCubit?.register();
                            if (_registerCubit?.state is RegisterFailure) {
                              _showError("prueba");
                            }
                            _registerCubit?.clean();
                            Navigator.pushReplacementNamed(context, '/login');
                          }
                        }
                      : () {},
                );
              }),
        ],
      ),
    );
  }

  void _showError(String error) {
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(error)));
  }
}
