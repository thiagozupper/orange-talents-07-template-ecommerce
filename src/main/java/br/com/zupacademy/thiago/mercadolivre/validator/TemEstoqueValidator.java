package br.com.zupacademy.thiago.mercadolivre.validator;

import br.com.zupacademy.thiago.mercadolivre.compra.NovaCompraRequest;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class TemEstoqueValidator implements ConstraintValidator<TemEstoque, NovaCompraRequest> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean isValid(NovaCompraRequest compra, ConstraintValidatorContext context) {

        Query query = em.createQuery("SELECT p.quantidadeDisponivel FROM Produto p WHERE p.id = :id");
        query.setParameter("id", compra.getProdutoId());
        Integer estoque = (Integer) query.getSingleResult();

        return estoque >= compra.getQuantidade() ? true : false;
    }
}
