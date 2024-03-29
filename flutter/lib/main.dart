import 'package:flutter/material.dart';
import 'dataservice.dart';
import 'dart:async';

import 'serverinfo.dart';
import 'startpage.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  MyApp({Key? key}) : super(key: key);

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  // This widget is the root of your application.
  late Future<List<ServerInfo>> futureData;

  @override
  void initState() {
    super.initState();
    futureData = fetchServerInfo();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Serverstatus monitor",
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: StartPage(title: "Server status monitor", serverInfo: futureData),
    );
  }
}
