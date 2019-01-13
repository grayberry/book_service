
CREATE TABLE if not exists categories(
                   id SERIAL primary key ,
                   category VARCHAR(255) unique not null
);

CREATE TABLE if not exists authors(
                    id SERIAL primary key ,
                    full_name VARCHAR(255) unique not null
);

CREATE TABLE books(
                    id SERIAL primary key ,
                    title VARCHAR(255) not null,
                    language VARCHAR(255)not null,
                    page_count INT NOT NULL,
                    image_ref VARCHAR(255),
                    description TEXT,
                    rating INTEGER default 0,
                    isbn_10 VARCHAR(255),
                    isbn_13 VARCHAR(255)
);

create table book_authors (
                    book_id integer references books(id) on update cascade on delete cascade,
                    author_id integer references authors(id) on update cascade on delete cascade,
                    PRIMARY KEY (book_id, author_id)
);

create table book_categories (
                    book_id integer references books(id) on update cascade on delete cascade,
                    category_id integer references categories(id) on update cascade on delete cascade,
                    PRIMARY KEY(book_id, category_id)
);