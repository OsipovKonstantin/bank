package ru.neoflex.deal.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.neoflex.deal.model.jsonb.EmploymentData;

import java.util.UUID;

@Entity
@Table(name = "employment")
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "employment_id")
    private UUID id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "employment_data", columnDefinition = "jsonb")
    private EmploymentData employmentData;

}
