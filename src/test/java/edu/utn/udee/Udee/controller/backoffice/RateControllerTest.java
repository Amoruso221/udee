package edu.utn.udee.Udee.controller.backoffice;

import edu.utn.udee.Udee.config.Conf;
import edu.utn.udee.Udee.service.backoffice.RateService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.bind.annotation.RestController;

import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Conf.class)
public class RateControllerTest {

    private RateController rateController;

    private RateService rateService;
    private ModelMapper modelMapper;

    @Before
    public void setUp() {
        this.rateService = mock(RateService.class);
        this.modelMapper = mock(ModelMapper.class);
        this.rateController = new RateController(rateService, modelMapper);
    }


}
