import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class CustomButton extends StatelessWidget {
  final Function()? onTap;
  final Color color;
  final double padding;
  final Color textColor;

  const CustomButton(
      {super.key,
      this.onTap,
      required this.color,
      required this.padding,
      required this.textColor});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.symmetric(horizontal: padding),
      child: InkWell(
        onTap: onTap,
        child: Ink(
          padding: EdgeInsets.all(12),
          decoration: BoxDecoration(
              color: color,
              borderRadius: BorderRadius.circular(8),
              boxShadow: [
                BoxShadow(
                    color: Colors.grey.withOpacity(.1),
                    spreadRadius: 2,
                    blurRadius: 5,
                    offset: Offset(0, 3))
              ]),
          child: Center(
              child: Text(
            'Login',
            style: GoogleFonts.montserrat(
                color: textColor, fontSize: 16, fontWeight: FontWeight.bold),
          )),
        ),
      ),
    );
  }
}
