package com.kgregorczyk.store.product.command;

import static com.google.common.base.Preconditions.checkArgument;

import com.kgregorczyk.store.cqrs.command.DomainCommandHandler;
import com.kgregorczyk.store.cqrs.command.DomainCommandPublisher;
import com.kgregorczyk.store.product.aggregate.ProductAggregate;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateProductCommandHandler
    extends DomainCommandHandler<ProductAggregate, CreateProductCommand> {

  @Autowired
  public CreateProductCommandHandler(DomainCommandPublisher<ProductAggregate> commandPublisher) {
    super(commandPublisher);
  }

  @Override
  protected void validateCommand(CreateProductCommand command) {
    checkArgument(command.name().length() > 3, "Product's name has to have 4 or more characters");
    checkArgument(command.price().compareTo(BigDecimal.ZERO) > 0,
        "Price has to be higher than 0.0");
  }

  @Override
  protected String getCommandTopic() {
    return ProductAggregate.COMMAND_TOPIC;
  }
}
