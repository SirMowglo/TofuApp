import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../bloc/authentication/authentication_bloc.dart';
import '../../config/locator.dart';
import '../../data/services/authentication_service.dart';
import '../../models/user.dart';

class HomePage extends StatelessWidget {
  final User user;
  
  const HomePage({super.key, required this.user});

  @override
  Widget build(BuildContext context) {
    final authBloc = BlocProvider.of<AuthenticationBloc>(context);
    return Scaffold(
      appBar: AppBar(
        title: Text('Home Page'),
      ),
      body: SafeArea(
        minimum: const EdgeInsets.all(16),
        child: Center(
          child: Column(
            children: <Widget>[
              Text(
                'Welcome, ${user.email}',
                style: TextStyle(
                  fontSize: 24
                ),
              ),
              const SizedBox(
                height: 12,
              ),
              ElevatedButton(

                child: Text('Logout'),
                onPressed: (){
                  authBloc.add(UserLoggedOutEvent());
                },
              ),
              ElevatedButton(onPressed: () async {
                JwtAuthenticationService service = getIt<JwtAuthenticationService>();
                await service.getCurrentUser();
              }
              , child: Text('Check')
              )
            ],
          ),
        ),
      ),
    );
  }
}
