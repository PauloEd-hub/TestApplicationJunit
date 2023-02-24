package br.ce.wcaquino.service;


import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.servicos.LocacaoService;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	
	@Test
	public void teste(){
		
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);
		
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		
		//verificacao Assert
//		Assert.assertEquals(locacao.getValor(), 5.0, 0.1);
//		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
//		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		
		
		
		
		//Verifcação AssertThat
		assertThat(null, null);
		assertThat(locacao.getValor(),CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		assertThat(locacao.getValor(),CoreMatchers.is(CoreMatchers.not(6.0)));
		assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), CoreMatchers.is(true));
		
	}

}
