%Facts
%%

human(john).
human(mary).
human(walsh).
human(jaime_armbriz).

schools(frankford_middle_school).
schools(highland_park_ISD).
schools(coppell_high_school).

organization(preston_office_space).
organization(executive_workspace).
organization(georgetown_office_community).


weekday(monday).
weekday(tuesday).
weekday(wedneday).
weekday(thursday).
weekday(friday).
weekend(saturday).
weekend(sunday).


age(john,20).
age(mary,17).

item(salad).
item(soup).
item(wraps).
item(pasta).
item(dessert).
item(pizza).
item(onions).
item(lemons).
item(cheesecake).
item(glutenfree).
item(california_club).
item(wild_salmon_wich).
item(bigchef).
item(chicken).
item(beef).
item(pork).
item(turkey).
item(fish).
item(snickerdoodle).
item(drinks).
item(packages).
item(paniniitems).
item(cheeseslices).
item(roastedbeef).
item(sandwiches).
item(kidsitems).
item(beefeater).
item(carmela).
item(plain_jane).
item(new_york_yankee).
item(chipotle_chicken).
item(carrot).
item(potatoes).
item(lettuce).
item(radish).
item(brinjal).
item(beans).
item(tomato).
item(broccoli).
item(cheesepizza).
item(tomato_basil).
item(chicken_salad).
item(turkey_wrap).
item(santafe_chicken).
item(shortcake).

manager(jaime_armbriz).

raw_meat(beef).
raw_meat(pork).
raw_meat(chicken).
raw_meat(fish).
raw_meat(turkey).

counter(salad,1).
counter(pasta,2).
counter(dessert,3).
counter(glutenfree,4).
counter(drinks,5).
counter(soup,6).
counter(sandwiches,7).
counter(packages,8).
counter(kidsitems,9).
counter(pizza,10).
counter(sides,11).

purchased(john,pizza,1).
purchased(john,cheesecake,2).
purchased(john,roastedbeef,1).
purchased(john,pasta,1).
purchased(john,beefeater,1).
purchased(mary,cheesecake,2).
purchased(mary,dessert,1).

location(jasondeli,coitroad).
established(jasondeli,1976).

contains(beefeater,beef).
contains(wild_salmon_wich,fish).
contains(california_club,chicken).
contains(plain_jane,pork).
contains(panini_item,chicken).
contains(roastedbeef,beef).
contains(bigchef,turkey).
contains(chipotle_chicken,chicken).
contains(new_york_yankee,beef).
contains(carmela,beef).
contains(pasta,chicken).
contains(pasta,ham).
contains(sandwiches,chicken).
contains(sandwiches,ham).
contains(soup,broccoli).
contains(kidsitems,cheesepizza).

vegetables(carrot).
vegetables(potatoes).
vegetables(radish).
vegetables(brinjal).
vegetables(beans).
vegetables(lettuce).
vegetables(tomato).
vegetables(onions).
vegetables(lemons).
vegetables(broccoli).

went(john,yesterday).
went(mary,yesterday).
went(walsh,today).

are_present(cheesepizza,kidsitems).
are_present(tomato_basil,soup).
are_present(chicken_salad,salad).
are_present(turkey_wrap,wraps).
are_present(santafe_chicken,sandwiches).
are_present(shortcake,dessert).

grows_in_deli(fruits).

money(cash).
money(card).
money(apple_pay).

%Rules
%%

person(X) :- human(X).
bought_at_deli(X) :- person(X).
deli(X) :- item(X).
registered_with_jasondeli(X) :- customer(X).
size(X) :- deli(X).
cost(X) :- deli(X).
edible(X) :- deli(X).
pay(X,Y,Z) :-buys(X,Z),money(Y),cost(Z).
buys(X,Y) :- person(X), deli(Y).
customer(X) :- person(X), deli(Y), buys(X,Y).
has(X,Y) :- customer(X) , purchased(X,Y,Z),Z>0.
sell(X) :- deli(X), not(in_sourced(X)).
bought_meat(X) :- customer(X),deli(Y),contains(Y,Z),raw_meat(Z),purchased(X,Y,K),K>0.
in_queue(Z,HOURS,MINUTES) :-customer(Z), HOURS>=10,HOURS<22,MINUTES>0,MINUTES<60.
hasmeat(X) :- deli(X),raw_meat(Y),contains(X,Y).
less_money(X) :- customer(X),pay(X,_,_),!.
vegetarian(X) :- not(hasmeat(Y)), customer(X),deli(Y),eats(X,Y).
cash_pay(X,Y,Z) :- buys(X,Z),pay(X,Y,Z),Y=cash.
card_pay(X,Y,Z) :- buys(X,Z),pay(X,Y,Z),Y=card.
apple_pay_method(X,Y,Z) :- buys(X,Z),pay(X,Y,Z),Y=apple_pay.
in_sourced(X) :- deli(X),(raw_meat(X) ; vegetables(X)).
sell_to_deli(X) :- deli(X),(in_sourced(X)).
made(X) :- deli(X) , not(sell_to_deli(X)).
opentime(HOURS,MINUTES) :- (HOURS>=10,HOURS<22,MINUTES>0,MINUTES<60) -> format('Store is Open') ;format('Store is Closed').
meet(X,Y,P2,P) :- customer(X), customer(Y),item(P2),buys(Y,P2),P2=cheesecake,went(X,P).
eats(X,Y) :- person(X) , deli(Y), purchased(X,Y,Z),Z>0.
carry_home(X,Y) :- customer(X),deli(Y),buys(X,Y).
best_selling(X) :- deli(X) , (X=new_york_yankee ; X=beefeater).
calorie_deficit(X) :- deli(X) , counter(X,Z), Z=7.
panini_item(X) :- deli(X) , counter(X,Z), Z=7.
prepared(X,Y) :- deli(X) , counter(X,Y).
chicken_pasta(Y) :-(deli(Y),contains(Y,Z),raw_meat(Z),Z=chicken,counter(Y,M),M=2)->format('The item is chicken pasta').
ham_sandwich(Y) :- (deli(Y),contains(Y,Z),raw_meat(Z),Z=pork,counter(Y,M),M=7)->format('The item is ham sandwich').
double_combo(X,Y) :- item(X),item(Y),counter(X,Z),(Z>0;Z<12),counter(Y,M),(M>0 ; M<12),Z \= M.
nitrite_free(Y) :- deli(Y),raw_meat(Y).
dont_sell_to_deli(X) :- deli(X),grows_in_deli(X).
has_veggies(Y) :- deli(Y),Y\=beef,(Y)\=turkey,(Y)\=chicken,(Y)\=fish,(Y)\=pork,(Y)\=dessert,Y\=drinks.
eats_veggies(X,M) :- customer(X),deli(Y),eats(X,Y),has_veggies(Y),went(X,M).
discounted_price(X,Y) :- deli(X) , weekday(Y), Y=thursday.
free_icecream(X) :- customer(X), deli(Y), buys(X,Y).
no_artificial_sweetners(X) :- deli(X).
isadult(X) :- not(customer(X)) -> format('The person did not dine at Jason deli');(customer(X),age(X,Y),Y>=18) -> format('Adult');(customer(X),age(X,Y),Y<18) -> format('Child').
eat_or_leave(X,Y) :- customer(X),purchased(X,Y,Z),Z>0->write('Eats it');write('Not purchased').
favourite(X,Y) :- customer(X) , deli(Y) , purchased(X,Y,Z),Z>1.
customize_item(X,Y) :- customer(X), item(Y) , counter(Y,Z), (Z>0 , Z<13).
reward_points(X) :- customer(X), deli(Y),buys(X,Y).
owns(X) :- (deli(X) -> write('Jason Deli owns the item')) ; write('Jason Deli does not own the item').
not_alone(X) :- (customer(X),customer(U),see(X,U,O)),went(X,O),O=yesterday->format('The restaurant has customers other than the customer');format('The customer is the only person & customer is accompanied by staff').
see(X,Y,O) :- customer(X),customer(Y),went(X,O),went(Y,O).
buy_in_bulk(X,Y) :- (schools(X);organization(X)),deli(Y),counter(Y,Z),Z=8.
to_buy(X,Y) :- customer(X), money(Y).
desserts(X) :- (deli(X),X=snickerdoodle,counter(X,Z),Z=3).
broccoli_soup(X) :- (deli(X),contains(X,Z),Z=broccoli,counter(X,T),T=6) -> format('It is broccoli soup').
kids(X) :- (deli(X), are_present(X,Y),counter(Y,Z),Z=9) -> format('Cheese pizza is a kids item').
typeof(X) :- (deli(X), are_present(X,Y),counter(Y,Z), Z=1) -> format('Chicken salad is sold in salad counter').
exotic_sandwich(X) :- (deli(X), are_present(X,Y),counter(Y,Z), Z=7) -> format('Santafe chicken is an exotic sandwich').
