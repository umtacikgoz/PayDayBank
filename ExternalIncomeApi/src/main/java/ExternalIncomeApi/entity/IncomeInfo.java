package ExternalIncomeApi.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "incomeInfo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
public class IncomeInfo {

    @Id
    @SequenceGenerator(name = "seq_income", allocationSize = 1)
    @GeneratedValue(generator = "seq_income", strategy = GenerationType.SEQUENCE)
    private Long id;

    public void setTckn(String tckn) {
        this.tckn = tckn;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getTckn() {
        return tckn;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    @Column(name = "tckn")
    private String tckn;

    @Column(name = "monthlyIncome")
    private double monthlyIncome;
}
