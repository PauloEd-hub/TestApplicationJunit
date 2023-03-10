package br.ce.wcaquino.service;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.servicos.LocacaoService;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	private LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		service = new LocacaoService();
	}
	

	@Test
	public void testeLOcacao() throws Exception {

		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// acao
		Locacao locacao = service.alugarFilme(usuario, filme);

		// Error
		error.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				CoreMatchers.is(true));
	}

	// verificacao Assert
//		Assert.assertEquals(locacao.getValor(), 5.0, 0.1);
//		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
//		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));

	// Verifcação AssertThat
//		assertThat(locacao.getValor(),CoreMatchers.is(CoreMatchers.equalTo(5.0)));
//		assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
//		assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), CoreMatchers.is(false));

	
	
	
	//Forma "ELEGANTE": Simples, enxuta e superficial
	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception {

		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// acao
		service.alugarFilme(usuario, filme);

	}
	
	
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		
		//cenario
		Filme filme = new Filme("Filme 2", 1, 4.0);
		
		//acao
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), CoreMatchers.is("Usuário vazio"));
		}
		
		
	}
	
	
	
	
	public void testLocacao_FilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		System.out.println("Test");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		//acao
		service.alugarFilme(usuario, null);
		
		
	}

//	@Test
//	public void testLocacao_filmeSemEstoque2() {
//
//		// cenario
//		LocacaoService service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario 1");
//		Filme filme = new Filme("Filme 1", 0, 5.0);
//
//		// acao
//		try {
//			service.alugarFilme(usuario, filme);
//			Assert.fail("Deveria ter lançado um aexcessão");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			Assert.assertThat(e.getMessage(), CoreMatchers.is("Filme sem estoque"));
//		}
//
//	}
//	
//	
//	
//	
//	@Test
//	public void testLocacao_filmeSemEstoque_3() throws Exception {
//
//		// cenario
//		LocacaoService service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario 1");
//		Filme filme = new Filme("Filme 1", 0, 5.0);
//		
//		
//		exception.expect(Exception.class);
//		exception.expectMessage("Filme sem estoque");
//
//		// acao
//		service.alugarFilme(usuario, filme);
//		
//	
//	}
	
}
