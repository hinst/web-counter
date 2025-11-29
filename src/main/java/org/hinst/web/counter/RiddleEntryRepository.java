package org.hinst.web.counter;

import org.springframework.data.repository.CrudRepository;

import jakarta.persistence.PersistenceException;

public interface RiddleEntryRepository extends CrudRepository<RiddleEntry, Long> {
	Object locker = new Object();
	int ATTEMPT_LIMIT = 10;

	default void saveNew(RiddleEntry riddleEntry) {
		for (var i = 0; i < ATTEMPT_LIMIT; ++i) {
			synchronized (locker) {
				if (existsById(riddleEntry.getId()))
					riddleEntry.setId(RandomUtil.getRandomLong());
				else {
					save(riddleEntry);
					return;
				}
			}
		}
		throw new PersistenceException("Cannot find unique ID for new riddle entry");
	}
}
