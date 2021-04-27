import 'serviceinfo.dart';

class ServerInfo {
  const ServerInfo({required this.name, required this.services});

  final String name;

  final List<ServiceInfo> services;

  factory ServerInfo.fromJson(Map<String, dynamic> json) {
    var list = (json['services'] as List)
            .map((p) => ServiceInfo.fromJson(p))
            .toList();
    return ServerInfo(
        name: json['name']!,
        services: list);
  }
  bool running() {
    bool ok = services.isNotEmpty;
    for (ServiceInfo service in services) {
      if (!service.running) {
        ok = false;
      }
    }

    return ok;
  }

  bool notAvailable() {
    return services.isEmpty;
  }
}
