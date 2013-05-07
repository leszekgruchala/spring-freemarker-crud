package eu.gruchala.crud.utils;

import eu.gruchala.crud.model.Person;

import org.joda.time.DateTime;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.hash.PrimitiveSink;

public class Sha256Hasher implements HashProvider<Person> {

    enum PersonFunnel implements Funnel<Person> {
        INSTANCE;

        @Override
        public void funnel(final Person person, final PrimitiveSink into) {
            into.putString(person.getEmail(), Charsets.UTF_8).putString(person.getName(), Charsets.UTF_8)
                .putString(DateTime.now().toString(), Charsets.UTF_8);
        }
    }

    @Override
    public String get(Person from) {
        final HashFunction hashFunction = Hashing.sha256();
        final Hasher hasher = hashFunction.newHasher();
        PersonFunnel.INSTANCE.funnel(from, hasher);
        return hasher.hash().toString();
    }
}
