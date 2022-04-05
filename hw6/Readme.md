# Домашнее задание

## Переписать приложение для хранения книг на ORM

**Цель:** полноценно работать с JPA + Hibernate для подключения к реляционным БД посредством ORM-фреймворка

**Результат:** Высокоуровневое приложение с JPA-маппингом сущностей

**Описание/Пошаговая инструкция выполнения домашнего задания:**

Домашнее задание выполняется переписыванием предыдущего на JPA.

**Требования:**

1. Использовать JPA, Hibernate только в качестве JPA-провайдера.
2. Для решения проблемы N+1 можно использовать специфические для Hibernate аннотации @Fetch и @BatchSize.
3. Добавить сущность "комментария к книге", реализовать CRUD для новой сущности.
4. Покрыть репозитории тестами, используя H2 базу данных и соответствующий H2 Hibernate-диалект для тестов.
    Не забудьте отключить DDL через Hibernate
5. @Transactional рекомендуется ставить только на методы сервиса. 
6. Это домашнее задание будет использоваться в качестве основы для других ДЗ Данная работа не засчитывает предыдущую!
