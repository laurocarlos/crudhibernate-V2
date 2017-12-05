package br.com.lapaz.hibernate.persistence;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.lapaz.hibernate.model.Lembrete;

public class Main {

	private static EntityManagerFactory entityManagerFactory;


	public void inserir(Lembrete lembrete) throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("hibernatejpa");
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(lembrete);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}finally {
			em.close();
		}
	}
	public void buscarTodos() throws Exception{

		entityManagerFactory = Persistence.createEntityManagerFactory("hibernatejpa");
		EntityManager em = entityManagerFactory.createEntityManager();
		List<Lembrete> lembretes = null;

		try {
			lembretes = em.createQuery("from Lembrete").getResultList();
		} catch (Exception e) {
			System.out.println("Listar todos:" + e.getMessage());
		}finally {
			em.close();
		}
		if(lembretes != null) {
			lembretes.forEach(System.out::println);
		}
	}
	public void buscarPorId(long id) {
		entityManagerFactory = Persistence.createEntityManagerFactory("hibernatejpa");
		EntityManager em = entityManagerFactory.createEntityManager();
		Lembrete lembrete = null;
		try {
			
			lembrete = em.find(Lembrete.class, id);
			System.out.println(lembrete);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			em.close();
		}if(lembrete == null) {
			System.out.println("Registro não encontrado.");
		}
		 
	}
	public void buscarPorNome(String titulo) {
		entityManagerFactory = Persistence.createEntityManagerFactory("hibernatejpa");
		EntityManager em = entityManagerFactory.createEntityManager();
		List<Lembrete> lembretes = null;
		try {
			Query query = em.createQuery("from Lembrete l where l.titulo like concat('%',?1,'%')");
			query.setParameter(1, titulo);
			lembretes = query.getResultList();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			em.close();
		}if(lembretes != null) {
			lembretes.forEach(System.out::println);
		}else if(lembretes == null) {
			System.out.println("Registro inexistente!");
		}
		 
	}
	public static void main(String[] args) throws Exception {
		/*
		Lembrete lembrete = new Lembrete();
		lembrete.setTitulo("Comprar Pão");
		lembrete.setDescricao("Ir na padaria comprar o pao, 8hrs");

		Main main = new Main();
		main.inserir(lembrete);
		 */
		/*
		Main main = new Main();
		main.buscarTodos();
		 */
		Main main = new Main();
		main.buscarPorNome("banho");
	}
}
