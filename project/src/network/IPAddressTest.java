package network;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

public class IPAddressTest {

	public static void main(String[] args) {
		Set<String> localIps = getLocalIps();
        System.out.println("##############Final List of IP's############");
        System.out.println(localIps);
	}

	public static Set<String> getLocalIps() {
		Set<String> localIps = new LinkedHashSet<String>();
		try {
			/* InetAddress inetAddr = InetAddress.getLocalHost(); populateIps(localIps, inetAddr); */
			Enumeration<NetworkInterface> enumNI = NetworkInterface.getNetworkInterfaces();
			while (enumNI.hasMoreElements()) {
				NetworkInterface ifc = enumNI.nextElement();
				if (ifc.isUp()) {
					Enumeration<InetAddress> enumAdds = ifc.getInetAddresses();
					while (enumAdds.hasMoreElements()) {
						InetAddress inetAddr = enumAdds.nextElement();
						populateIps(localIps, inetAddr);
					}
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return localIps;
	}

	private static void populateIps(Set<String> localIps, InetAddress inetAddr) throws UnknownHostException {
		String localAddr = inetAddr.getHostAddress();
		System.out.println("---------Fetching multiple ip addresses for: " + localAddr + "------------");
		// Just in case this host has multiple IP addresses....
		InetAddress[] allMyIps = InetAddress.getAllByName(inetAddr.getCanonicalHostName());
		if (allMyIps != null && allMyIps.length > 1) {
			for (int i = 0; i < allMyIps.length; i++) {
				InetAddress multiInetAddr = allMyIps[i];
				String addr = multiInetAddr.getHostAddress();
				if (multiInetAddr instanceof Inet4Address) {
					System.out.println("Ipv4 address: " + addr);
					localIps.add(addr);
				} else {
					System.out.println("Not an ipv4 address: " + addr);
				}
			}
		} else {
			System.out.println("Unable to find multiple ip addresses for: " + localAddr);
		}
	}
}
