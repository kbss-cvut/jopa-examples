///
/// JOPA Examples
/// Copyright (C) 2024 Czech Technical University in Prague
///
/// This library is free software; you can redistribute it and/or
/// modify it under the terms of the GNU Lesser General Public
/// License as published by the Free Software Foundation; either
/// version 3.0 of the License, or (at your option) any later version.
///
/// This library is distributed in the hope that it will be useful,
/// but WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
/// Lesser General Public License for more details.
///
/// You should have received a copy of the GNU Lesser General Public
/// License along with this library.
///

import {applyMiddleware, compose, createStore, Middleware} from "redux";
import thunk, {ThunkMiddleware} from "redux-thunk";
import AppReducers from "../reducer/AppReducers";

const composeEnhancers = (window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

const middlewares: Middleware[] = [thunk as ThunkMiddleware];

const AppStore = createStore(
    AppReducers,
    composeEnhancers(applyMiddleware(...middlewares))
);

export default AppStore;
