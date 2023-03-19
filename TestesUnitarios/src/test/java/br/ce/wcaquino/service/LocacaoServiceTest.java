package br.ce.wcaquino.service;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import javax.naming.spi.DirStateFactory.Result;
import javax.xml.crypto.Data;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
	public void deveAlugarFilmeComSucesso() throws Exception {
		
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

		// acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

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
	public void deveLancarExcessaoAoAlugarFilmeSemEstoque() throws Exception {

		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		// acao
		service.alugarFilme(usuario, filmes);

	}
	
	
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		
		//cenario
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));
		
		//acao
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), CoreMatchers.is("Usuário vazio"));
		}
		
		
	}
	
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		System.out.println("Test");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		//acao
		service.alugarFilme(usuario, null);
		
	}
	
//	@Test
//	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
//		
//		//cenario
//		Usuario usuario = new Usuario("Usuario 1");
//		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0), new Filme("Filme 3", 2, 4.0));
//		
//		//acao
//		Locacao resultado = service.alugarFilme(usuario, filmes);
//		
//		//verificacao
//		assertThat(resultado.getValor(), CoreMatchers.is(11.0));
//		
//	}
//	
//	@Test
//	public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
//		
//		//cenario
//		Usuario usuario = new Usuario("Usuario 1");
//		List<Filme> filmes = Arrays.asList(
//				new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0), new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0));
//		
//		//acao
//		Locacao resultado = service.alugarFilme(usuario, filmes);
//		
//		//verificacao
//		assertThat(resultado.getValor(), CoreMatchers.is(13.0));
//		
//	}
//	
//	@Test
//	public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
//		
//		//cenario
//		Usuario usuario = new Usuario("Usuario 1");
//		List<Filme> filmes = Arrays.asList(
//				new Filme("Filme 1", 2, 4.0), 
//				new Filme("Filme 2", 2, 4.0), 
//				new Filme("Filme 3", 2, 4.0), 
//				new Filme("Filme 4", 2, 4.0), 
//				new Filme("Filme 5", 2, 4.0));
//		
//		//acao
//		Locacao resultado = service.alugarFilme(usuario, filmes);
//		
//		//verificacao
//		assertThat(resultado.getValor(), CoreMatchers.is(14.0));
//		
//	}
//	
//	@Test
//	public void devePagar0PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
//		
//		//cenario
//		Usuario usuario = new Usuario("Usuario 1");
//		List<Filme> filmes = Arrays.asList(
//				new Filme("Filme 1", 2, 4.0), 
//				new Filme("Filme 2", 2, 4.0), 
//				new Filme("Filme 3", 2, 4.0), 
//				new Filme("Filme 4", 2, 4.0), 
//				new Filme("Filme 5", 2, 4.0),
//				new Filme("Filme 6", 2, 4.0));
//		
//		//acao
//		Locacao resultado = service.alugarFilme(usuario, filmes);
//		
//		//verificacao
//		
//		assertThat(resultado.getValor(), CoreMatchers.is(14.0));
//		
//	}
	
	@Test
//	@Ignore
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cenário
		Usuario usuario = new Usuario("Usuário 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1,", 1, 5.0));
		
		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		//verificacao
		boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
		Assert.assertTrue(ehSegunda);
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
