package ch.boxi.ants.helper;

import java.util.UUID;

public class IdGenerator {
	public static String getNextUniqueID() {
		UUID id = UUID.randomUUID();
		return id.toString();
	}
}
