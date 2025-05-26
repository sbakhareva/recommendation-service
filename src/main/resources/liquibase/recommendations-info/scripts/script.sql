-- liquibase formatted sql

-- changeset ros1nka:1
CREATE TABLE IF NOT EXISTS recommendations (
id VARCHAR(50) NOT NULL,
name VARCHAR(50) NOT NULL,
description VARCHAR(255) NOT NULL
)

-- changeset ros1nka:2
ALTER TABLE recommendations ALTER COLUMN id TYPE UUID;
ALTER TABLE recommendations ALTER COLUMN description TYPE TEXT;

-- changeset ros1nka:3
INSERT INTO recommendations (id, name, description) VALUES
('147f6a0f-3b91-413b-ab99-87f081d60d5a', 'Invest 500', 'Откройте свой путь к успеху с индивидуальным инвестиционным
счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом.
Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде.
Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями.
Откройте ИИС сегодня и станьте ближе к финансовой независимости!'),

('59efc529-2fff-41af-baff-90ccd7402925', 'Top Saving', 'Откройте свою собственную «Копилку» с нашим банком!
«Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели.
Больше никаких забытых чеков и потерянных квитанций — всё под контролем!
Преимущества «Копилки»:
Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.
Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.
Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.
Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!'),

('ab138afb-f3ba-4a93-b74f-0fcee86d447f', 'Simple Loan', 'Откройте мир выгодных кредитов с нами!
Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно!
 Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.
Почему выбирают нас:
Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.
Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.
Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.
Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!');

-- changeset sbakhareva:5
CREATE TABLE rules (
id UUID PRIMARY KEY NOT NULL,
query VARCHAR(50) NOT NULL,
arguments VARCHAR(255) NOT NULL,
negate BOOLEAN NOT NULL,
recommendation_id UUID
)

-- changeset sbakhareva:6
ALTER TABLE recommendations
ADD CONSTRAINT pk_recommendations_id PRIMARY KEY (id);
ALTER TABLE rules
ADD CONSTRAINT fk_recommendations
FOREIGN KEY (recommendation_id) REFERENCES recommendations(id);

-- changeset sbakhareva:7
ALTER TABLE rules ALTER COLUMN arguments TYPE BLOB;

-- changeset sbakhareva:12
ALTER TABLE rules ALTER COLUMN arguments TYPE BLOB;