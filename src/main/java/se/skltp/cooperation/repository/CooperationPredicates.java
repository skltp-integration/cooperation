package se.skltp.cooperation.repository;

import com.mysema.query.types.Predicate;
import se.skltp.cooperation.domain.QCooperation;

/**
 * A class with QueryDSL Predicates for {@link se.skltp.cooperation.domain.Cooperation}
 *
 * @author Peter Merikan
 */
public class CooperationPredicates {

    public static Predicate serviceConsumerIdIs(final Long id) {
        QCooperation cooperation = QCooperation.cooperation;
        return cooperation.serviceConsumer.id.eq(id);
    }

    public static Predicate logicalAddressIdIs(final Long id) {
        QCooperation cooperation = QCooperation.cooperation;
        return cooperation.logicalAddress.id.eq(id);
    }

    public static Predicate serviceContractIdIs(final Long id) {
        QCooperation cooperation = QCooperation.cooperation;
        return cooperation.serviceContract.id.eq(id);
    }

    public static Predicate connectionPointIdIs(final Long id) {
        QCooperation cooperation = QCooperation.cooperation;
        return cooperation.connectionPoint.id.eq(id);
    }

}
