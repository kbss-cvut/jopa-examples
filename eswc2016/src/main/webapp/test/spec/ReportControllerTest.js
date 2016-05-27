'use strict';

describe('Report controller', function () {

    var React = require('react'),
        TestUtils = require('react-addons-test-utils'),
        Environment = require('../environment/Environment'),
        ReportController = require('../../js/components/ReportController').default,
        Actions = require('../../js/actions/Actions');

    beforeEach(function () {
        spyOn(Actions, 'loadReport');
    });

    it('loads report when report key is passed in props', function () {
        var params = {
            reportKey: 12345
        },
        component = Environment.render(<ReportController params={params}/>);

        expect(Actions.loadReport).toHaveBeenCalledWith(params.reportKey);
    });
    
    it('creates new report when no key is passed in props', function() {
        var factory = require('../../js/util/InstanceFactory'),
            component;
        spyOn(factory, 'createReport').and.callThrough();

        component = Environment.render(<ReportController params={{}}/>);
        expect(factory.createReport).toHaveBeenCalled();
    });

    it('renders mask until report is loaded', function() {
        var params = {
                reportKey: 12345
            },
            mask,
            Mask = require('../../js/components/Mask').default,
            component = Environment.render(<ReportController params={params}/>);
        mask = TestUtils.scryRenderedComponentsWithType(component, Mask);
        expect(mask).not.toBeNull();
    });

    it('creates report when save is called for new report', function() {
        var component = Environment.render(<ReportController params={{}}/>);
        spyOn(Actions, 'createReport');
        
        component._onSave();
        expect(Actions.createReport).toHaveBeenCalled();
    });

    it('saves report when save is called for an existing report', function() {
        var params = {
                reportKey: 12345
            },
            component = Environment.render(<ReportController params={params}/>),
            report = {
                key: params.reportKey,
                documents: {
                    title: 'Test',
                    date: Date.now()
                }
            };
        component._onReportLoaded({
            action: Actions.loadReport,
            report: report
        });
        spyOn(Actions, 'updateReport');
        component._onSave();
        expect(Actions.updateReport).toHaveBeenCalled();
    });
});
