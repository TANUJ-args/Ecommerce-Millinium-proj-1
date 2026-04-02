package ai.tanuj.ecomerce.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ai.tanuj.ecomerce.Repository.ProductRepository;

@Component
public class ProductVersionInitializer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ProductVersionInitializer.class);

    private final ProductRepository productRepository;

    public ProductVersionInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        int updatedRows = productRepository.backfillNullVersions();
        if (updatedRows > 0) {
            logger.info("Backfilled null product version values for {} row(s).", updatedRows);
        }
    }
}
