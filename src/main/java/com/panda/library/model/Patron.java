    package com.panda.library.model;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.Pattern;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.hibernate.annotations.DialectOverride;
    import org.hibernate.annotations.Where;

    @Setter
    @Getter
    @Entity
    @NoArgsConstructor
    @Where(clause = "deleted = false")
    public class Patron {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @NotBlank
        @Column(nullable = false)
        private String name;
        @NotBlank
        @Column(unique = true,nullable = false)
        @Pattern(
                regexp = "^\\+?[1-9]\\d{1,14}$  ",
                message = "must be a real phone number"
        )
        private String phoneNumber;

        @JsonIgnore
        @Column(name = "deleted")
        private boolean deleted = false;

        public Patron(Long id, String name, String phoneNumber) {
            this.id = id;
            this.name = name;
            this.phoneNumber = phoneNumber;
        }
    }
