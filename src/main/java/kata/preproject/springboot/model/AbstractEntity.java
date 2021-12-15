package kata.preproject.springboot.model;

import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractEntity<PK extends Number> implements Persistable<PK> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private PK id;

    @Nullable
    @Override
    public PK getId() {
        return id;
    }

    public void setId(@Nullable PK id) {
        this.id = id;
    }

    @Transient
    @Override
    public boolean isNew() {
        return null == getId();
    }

}
