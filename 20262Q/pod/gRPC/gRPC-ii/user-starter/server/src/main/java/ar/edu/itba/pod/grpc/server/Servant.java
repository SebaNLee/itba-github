package ar.edu.itba.pod.grpc.server;

import ar.edu.itba.pod.grpc.user.*;
import io.grpc.stub.StreamObserver;

import org.springframework.stereotype.Service;

import module java.base;

@Service
public class Servant extends UserServiceGrpc.UserServiceImplBase {

	/**
	 * Sample Data
	 */
	private static final Map<String, String> passwordsByUser = Map.of(
			"foo", "foopass",
			"bar", "barpass");
	private static final Map<String, User> users = Map.of(
			"foo", User.newBuilder()
					.setUserName("foo")
					.setDisplayName("Foo")
					.setStatus(AccountStatus.ACCOUNT_STATUS_ACTIVE)
					.addPreferences("darkMode")
					.addPreferences("liteView").build(),
			"bar", User.newBuilder()
					.setUserName("bar")
					.setDisplayName("Bar")
					.setStatus(AccountStatus.ACCOUNT_STATUS_PENDING)
					.addPreferences("lightMode").build());
	private final Map<String, UserRoles> userRolesMap = Map.of(
			"foo", UserRoles.newBuilder()
					.putRolesBySite("abc.com", Role.ADMIN)
					.putRolesBySite("xyz.com", Role.BUYER).build(),
			"bar", UserRoles.newBuilder()
					.putRolesBySite("abc.com", Role.SELLER).build());


	@Override
	public void doLogin(LoginInformation loginInformation, StreamObserver<User> responseObserver) {
		String username = loginInformation.getUserName();
		String password = loginInformation.getPassword();

		if (passwordsByUser.containsKey(username) && passwordsByUser.get(username).equals(password)) {
			responseObserver.onNext(users.get(username));
		}
		
		responseObserver.onCompleted();
	}

	@Override
	public void getRoles(User user, StreamObserver<UserRoles> responseObserver) {
		String username = user.getUserName();

		if (userRolesMap.containsKey(username)) {
			responseObserver.onNext(userRolesMap.get(username));
		}

		responseObserver.onCompleted();
	}
}
