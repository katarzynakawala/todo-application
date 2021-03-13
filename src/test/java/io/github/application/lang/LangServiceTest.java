package io.github.application.lang;


import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LangServiceTest {
    @Test
    public void serviceWorks(){
        //given
        LangRepository repository= Mockito.mock(LangRepository.class);
        List<Lang> langs = new ArrayList<>();
        langs.add(new Lang(1, "hej", "pl"));
        Mockito.when(repository.findAll()).thenReturn(langs);

        LangService langService = new LangService(repository);
        //when
        List<LangDTO> all = langService.findAll();
        //then
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getCode()).isEqualTo("pl");
        assertThat(all.get(0).getId()).isEqualTo(1);
    }
}
