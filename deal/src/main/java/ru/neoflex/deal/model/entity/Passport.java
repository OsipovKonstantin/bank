package ru.neoflex.deal.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.neoflex.deal.model.jsonb.PassportData;

import java.util.UUID;

@Entity
@Table(name = "passport")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "passport_id")
    private UUID id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "passport_data", columnDefinition = "jsonb")
    private PassportData passportData;

}
