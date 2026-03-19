package api.dev.domain.master.model.aggregate;

import api.dev.domain.master.model.entity.Master;
import api.dev.domain.master.model.entity.MasterProfile;
import api.dev.domain.shared.valueobject.Email;
import api.dev.domain.user.model.entity.User;

/**
 * Aggregate root that owns Master + User + MasterProfile together.
 * Use this in commands that need to create or load the full aggregate.
 */
public class MasterAggregate {

    private final User user;
    private final Master master;

    private MasterAggregate(User user, Master master) {
        this.user   = user;
        this.master = master;
    }

    public static MasterAggregate create(User user, Master master) {
        return new MasterAggregate(user, master);
    }

    public User getUser()     { return user; }
    public Master getMaster() { return master; }
    public MasterProfile getProfile() { return master.getProfile(); }

    public Email getEmail()   { return user.getEmail(); }
    public boolean isActive() { return master.isActive(); }
    public boolean isBanned() { return master.isBanned(); }
}
