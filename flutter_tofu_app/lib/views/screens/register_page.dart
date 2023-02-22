import 'package:flutter/material.dart';

class RegisterPage extends StatelessWidget {
  const RegisterPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color.fromARGB(255, 230, 230, 230),
      appBar: AppBar(
        backgroundColor: Color.fromARGB(255, 155, 214, 100),
        title: Row(children: [
          Icon(size: 35,Icons.person),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 20.0),
            child: Text('Register'),
          )
        ]),
      ),
      body: Text('buena tarde'),
    );
  }
}