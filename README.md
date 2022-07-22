# Mockito Demonstration

## Introduction

[Mockito](https://site.mockito.org/) is a fantastic library for testing software that either isn't complete, or cannot
be testing in a complete environment, for example where it needs to connect to a database, but this isn't available.

This example is based around the concept of a simple shop. There are two main classes:

- [Product](src/main/java/uk/hpkns/mockitodemo/Product.java)
  Well, the shop needs something on its shelves! These have a barcode, name and price, as you'd expect.
- [Purchase](src/main/java/uk/hpkns/mockitodemo/Purchase.java)
  When you pick up your new favourite items and take them to the till, they become a purchase. Once you've paid, this
  could be stored into a database, or printed onto a receipt. Maybe both.

Any other classes used should be introduced as you work through the tests.

The key thing to remember is that this example is _incomplete_. It isn't intended to be a full representation, but shows
how you can use Mockito to test incomplete projects.

## Getting Started

Try cloning the repository, then running `mvn clean test`. It is designed to require at least Java 17 to run, but might
work with older versions if you want to put the effort in to adjust some bits of it...

Then, work through the [WhyMockitoTests.java](src/test/java/uk/hpkns/mockitodemo/WhyMockitoTests.java) test suite, which
walks through why Mockito is useful, and how to use it.
